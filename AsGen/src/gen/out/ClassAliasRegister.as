package {

	import flash.net.registerClassAlias;
	import flash.utils.getQualifiedClassName;
	import gen.dto.core.*;

	public class ClassAliasRegister {

        public static var dtos:Array = [
            gen.dto.core.PlayerItemDto 
        ];

		public static function registerAllModel():void {

            for each(var cls:Class in dtos) {
                registerClassAlias(getQualifiedClassName(cls).replace("::","."), cls);
            }
		}
	}
}
