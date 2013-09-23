// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/wabao/mogame/protocol/dto/primitive/StringDto_Proto.proto

package com.wabao.mogame.protocol.dto.primitive;

public final class StringDtoProto {
  private StringDtoProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface StringDtoOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional string value = 1;
    /**
     * <code>optional string value = 1;</code>
     */
    boolean hasValue();
    /**
     * <code>optional string value = 1;</code>
     */
    java.lang.String getValue();
    /**
     * <code>optional string value = 1;</code>
     */
    com.google.protobuf.ByteString
        getValueBytes();
  }
  /**
   * Protobuf type {@code com.wabao.mogame.protocol.dto.primitive.StringDto}
   *
   * <pre>
   **
   *字段列表:
   *value	null
   * </pre>
   */
  public static final class StringDto extends
      com.google.protobuf.GeneratedMessage
      implements StringDtoOrBuilder {
    // Use StringDto.newBuilder() to construct.
    private StringDto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private StringDto(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final StringDto defaultInstance;
    public static StringDto getDefaultInstance() {
      return defaultInstance;
    }

    public StringDto getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private StringDto(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      initFields();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              bitField0_ |= 0x00000001;
              value_ = input.readBytes();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e.getMessage()).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.class, com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.Builder.class);
    }

    public static com.google.protobuf.Parser<StringDto> PARSER =
        new com.google.protobuf.AbstractParser<StringDto>() {
      public StringDto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new StringDto(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<StringDto> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional string value = 1;
    public static final int VALUE_FIELD_NUMBER = 1;
    private java.lang.Object value_;
    /**
     * <code>optional string value = 1;</code>
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional string value = 1;</code>
     */
    public java.lang.String getValue() {
      java.lang.Object ref = value_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          value_ = s;
        }
        return s;
      }
    }
    /**
     * <code>optional string value = 1;</code>
     */
    public com.google.protobuf.ByteString
        getValueBytes() {
      java.lang.Object ref = value_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        value_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private void initFields() {
      value_ = "";
    }
    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized != -1) return isInitialized == 1;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        output.writeBytes(1, getValueBytes());
      }
      getUnknownFields().writeTo(output);
    }

    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.CodedOutputStream
          .computeBytesSize(1, getValueBytes());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    protected java.lang.Object writeReplace()
        throws java.io.ObjectStreamException {
      return super.writeReplace();
    }

    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code com.wabao.mogame.protocol.dto.primitive.StringDto}
     *
     * <pre>
     **
     *字段列表:
     *value	null
     * </pre>
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.class, com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.Builder.class);
      }

      // Construct using com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessage.alwaysUseFieldBuilders) {
        }
      }
      private static Builder create() {
        return new Builder();
      }

      public Builder clear() {
        super.clear();
        value_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor;
      }

      public com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto getDefaultInstanceForType() {
        return com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.getDefaultInstance();
      }

      public com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto build() {
        com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto buildPartial() {
        com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto result = new com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.value_ = value_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto) {
          return mergeFrom((com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto other) {
        if (other == com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto.getDefaultInstance()) return this;
        if (other.hasValue()) {
          bitField0_ |= 0x00000001;
          value_ = other.value_;
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.wabao.mogame.protocol.dto.primitive.StringDtoProto.StringDto) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional string value = 1;
      private java.lang.Object value_ = "";
      /**
       * <code>optional string value = 1;</code>
       */
      public boolean hasValue() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional string value = 1;</code>
       */
      public java.lang.String getValue() {
        java.lang.Object ref = value_;
        if (!(ref instanceof java.lang.String)) {
          java.lang.String s = ((com.google.protobuf.ByteString) ref)
              .toStringUtf8();
          value_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>optional string value = 1;</code>
       */
      public com.google.protobuf.ByteString
          getValueBytes() {
        java.lang.Object ref = value_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          value_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>optional string value = 1;</code>
       */
      public Builder setValue(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional string value = 1;</code>
       */
      public Builder clearValue() {
        bitField0_ = (bitField0_ & ~0x00000001);
        value_ = getDefaultInstance().getValue();
        onChanged();
        return this;
      }
      /**
       * <code>optional string value = 1;</code>
       */
      public Builder setValueBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        value_ = value;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.wabao.mogame.protocol.dto.primitive.StringDto)
    }

    static {
      defaultInstance = new StringDto(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.wabao.mogame.protocol.dto.primitive.StringDto)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n=com/wabao/mogame/protocol/dto/primitiv" +
      "e/StringDto_Proto.proto\022\'com.wabao.mogam" +
      "e.protocol.dto.primitive\"\032\n\tStringDto\022\r\n" +
      "\005value\030\001 \001(\t"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_wabao_mogame_protocol_dto_primitive_StringDto_descriptor,
              new java.lang.String[] { "Value", });
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}
