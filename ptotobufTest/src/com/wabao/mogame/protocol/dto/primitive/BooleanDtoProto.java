// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: com/wabao/mogame/protocol/dto/primitive/BooleanDto_Proto.proto

package com.wabao.mogame.protocol.dto.primitive;

public final class BooleanDtoProto {
  private BooleanDtoProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public interface BooleanDtoOrBuilder
      extends com.google.protobuf.MessageOrBuilder {

    // optional bool value = 1;
    /**
     * <code>optional bool value = 1;</code>
     */
    boolean hasValue();
    /**
     * <code>optional bool value = 1;</code>
     */
    boolean getValue();
  }
  /**
   * Protobuf type {@code com.wabao.mogame.protocol.dto.primitive.BooleanDto}
   */
  public static final class BooleanDto extends
      com.google.protobuf.GeneratedMessage
      implements BooleanDtoOrBuilder {
    // Use BooleanDto.newBuilder() to construct.
    private BooleanDto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
      this.unknownFields = builder.getUnknownFields();
    }
    private BooleanDto(boolean noInit) { this.unknownFields = com.google.protobuf.UnknownFieldSet.getDefaultInstance(); }

    private static final BooleanDto defaultInstance;
    public static BooleanDto getDefaultInstance() {
      return defaultInstance;
    }

    public BooleanDto getDefaultInstanceForType() {
      return defaultInstance;
    }

    private final com.google.protobuf.UnknownFieldSet unknownFields;
    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
        getUnknownFields() {
      return this.unknownFields;
    }
    private BooleanDto(
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
            case 8: {
              bitField0_ |= 0x00000001;
              value_ = input.readBool();
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
      return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor;
    }

    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.class, com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.Builder.class);
    }

    public static com.google.protobuf.Parser<BooleanDto> PARSER =
        new com.google.protobuf.AbstractParser<BooleanDto>() {
      public BooleanDto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new BooleanDto(input, extensionRegistry);
      }
    };

    @java.lang.Override
    public com.google.protobuf.Parser<BooleanDto> getParserForType() {
      return PARSER;
    }

    private int bitField0_;
    // optional bool value = 1;
    public static final int VALUE_FIELD_NUMBER = 1;
    private boolean value_;
    /**
     * <code>optional bool value = 1;</code>
     */
    public boolean hasValue() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>optional bool value = 1;</code>
     */
    public boolean getValue() {
      return value_;
    }

    private void initFields() {
      value_ = false;
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
        output.writeBool(1, value_);
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
          .computeBoolSize(1, value_);
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

    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseDelimitedFrom(input, extensionRegistry);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return PARSER.parseFrom(input);
    }
    public static com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return PARSER.parseFrom(input, extensionRegistry);
    }

    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto prototype) {
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
     * Protobuf type {@code com.wabao.mogame.protocol.dto.primitive.BooleanDto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder>
       implements com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor;
      }

      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.class, com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.Builder.class);
      }

      // Construct using com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.newBuilder()
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
        value_ = false;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public Builder clone() {
        return create().mergeFrom(buildPartial());
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor;
      }

      public com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto getDefaultInstanceForType() {
        return com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.getDefaultInstance();
      }

      public com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto build() {
        com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto buildPartial() {
        com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto result = new com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto(this);
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
        if (other instanceof com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto) {
          return mergeFrom((com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto other) {
        if (other == com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto.getDefaultInstance()) return this;
        if (other.hasValue()) {
          setValue(other.getValue());
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
        com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto) e.getUnfinishedMessage();
          throw e;
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      // optional bool value = 1;
      private boolean value_ ;
      /**
       * <code>optional bool value = 1;</code>
       */
      public boolean hasValue() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>optional bool value = 1;</code>
       */
      public boolean getValue() {
        return value_;
      }
      /**
       * <code>optional bool value = 1;</code>
       */
      public Builder setValue(boolean value) {
        bitField0_ |= 0x00000001;
        value_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional bool value = 1;</code>
       */
      public Builder clearValue() {
        bitField0_ = (bitField0_ & ~0x00000001);
        value_ = false;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:com.wabao.mogame.protocol.dto.primitive.BooleanDto)
    }

    static {
      defaultInstance = new BooleanDto(true);
      defaultInstance.initFields();
    }

    // @@protoc_insertion_point(class_scope:com.wabao.mogame.protocol.dto.primitive.BooleanDto)
  }

  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n>com/wabao/mogame/protocol/dto/primitiv" +
      "e/BooleanDto_Proto.proto\022\'com.wabao.moga" +
      "me.protocol.dto.primitive\"\033\n\nBooleanDto\022" +
      "\r\n\005value\030\001 \001(\010"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_wabao_mogame_protocol_dto_primitive_BooleanDto_descriptor,
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
