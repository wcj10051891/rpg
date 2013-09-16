/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.json;

import java.lang.ref.SoftReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.util.JSONUtils;
import net.sf.json.util.JsonEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for JSONObject and JSONArray.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
abstract class AbstractJSON {
   private static class CycleSet extends ThreadLocal {
      protected Object initialValue() {
         return new SoftReference(new HashSet());
      }

      public Set getSet() {
         Set set = (Set) ((SoftReference)get()).get();
         if( set == null ) {
             set = new HashSet();
             set(new SoftReference(set));
         }
         return set;
      }
   }
   
   private static CycleSet cycleSet = new CycleSet();

   private static final Log log = LogFactory.getLog( AbstractJSON.class );

   /**
    * Adds a reference for cycle detection check.
    *
    * @param instance the reference to add
    * @return true if the instance has not been added previously, false
    *        otherwise.
    */
   protected static boolean addInstance( Object instance ) {
      return getCycleSet().add( instance );
   }

   /**
    * Removes a reference for cycle detection check.
    */
   protected static void removeInstance( Object instance ) {
      Set set = getCycleSet();
      set.remove( instance );
      if(set.size() == 0) {
          cycleSet.remove();
      }
   }

   protected Object _processValue( Object value, JsonConfig jsonConfig ) {
      if( JSONNull.getInstance().equals( value ) ) {
         return JSONNull.getInstance();
      } else if( Class.class.isAssignableFrom( value.getClass() ) || value instanceof Class ) {
         return ((Class) value).getName();
      } else if( JSONUtils.isFunction( value ) ) {
         if( value instanceof String ) {
            value = JSONFunction.parse( (String) value );
         }
         return value;
      } else if( value instanceof JSONString ) {
         return JSONSerializer.toJSON( (JSONString) value, jsonConfig );
      } else if( value instanceof JSON ) {
         return JSONSerializer.toJSON( value, jsonConfig );
      } else if( JSONUtils.isArray( value ) ) {
         return JSONArray.fromObject( value, jsonConfig );
      } else if( JSONUtils.isString( value ) ) {
         String str = String.valueOf( value );
         if( JSONUtils.hasQuotes( str ) ){
            String stripped = JSONUtils.stripQuotes( str );
            if( JSONUtils.isFunction( stripped )){
               return JSONUtils.DOUBLE_QUOTE + stripped + JSONUtils.DOUBLE_QUOTE;
            }
            if(stripped.startsWith("[") && stripped.endsWith("]")) {
               return stripped;
            }
            if(stripped.startsWith("{") && stripped.endsWith("}")) {
               return stripped;
            }
            return str;
         } else if( JSONUtils.isJsonKeyword( str, jsonConfig ) ) {
            if( jsonConfig.isJavascriptCompliant() && "undefined".equals( str )){
               return JSONNull.getInstance();
            }
            return str;
         } else if( JSONUtils.mayBeJSON( str ) ) {
            try {
               return JSONSerializer.toJSON( str, jsonConfig );
            } catch( JSONException jsone ) {
               return str;
            }
         }
         return str;
      } else if( JSONUtils.isNumber( value ) ) {
         JSONUtils.testValidity( value );
         return JSONUtils.transformNumber( (Number) value );
      } else if( JSONUtils.isBoolean( value ) ) {
         return value;
      } else {
         JSONObject jsonObject = JSONObject.fromObject( value, jsonConfig );
         if( jsonObject.isNullObject() ) {
            return JSONNull.getInstance();
         } else {
            return jsonObject;
         }
      }
   }
   
   private static Set getCycleSet() {
      return cycleSet.getSet();
   }
}