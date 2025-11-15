package cn.crabapples.common.config;

import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * fastJson2配置
 *
 * @author Ms.He
 * 2024/3/19 22:19
 * e-mail crabapples.cn@gmail.com
 * qq 294046317
 * pc-name mshe
 */
@Configuration
public class FastJson2Configure {
    @Bean
    public FastJsonConfig fastJsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.WriteNullListAsEmpty);
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.WriteNullStringAsEmpty);
        fastJsonConfig.setWriterFeatures(JSONWriter.Feature.WriteNulls);
//        FieldBased(1L),
//                IgnoreNoneSerializable(2L),
//                ErrorOnNoneSerializable(4L),
//                BeanToArray(8L),
//                WriteNulls(16L),
//                WriteMapNullValue(16L),
//                BrowserCompatible(32L),
//                NullAsDefaultValue(64L),
//                WriteBooleanAsNumber(128L),
//                WriteNonStringValueAsString(256L),
//                WriteClassName(512L),
//                NotWriteRootClassName(1024L),
//                NotWriteHashMapArrayListClassName(2048L),
//                NotWriteDefaultValue(4096L),
//                WriteEnumsUsingName(8192L),
//                WriteEnumUsingToString(16384L),
//                IgnoreErrorGetter(32768L),
//                PrettyFormat(65536L),
//                ReferenceDetection(131072L),
//                WriteNameAsSymbol(262144L),
//                WriteBigDecimalAsPlain(524288L),
//                UseSingleQuotes(1048576L),
//                MapSortField(2097152L),
//                WriteNullListAsEmpty(4194304L),
//                WriteNullStringAsEmpty(8388608L),
//                WriteNullNumberAsZero(16777216L),
//                WriteNullBooleanAsFalse(33554432L),
//                NotWriteEmptyArray(67108864L),
//                WriteNonStringKeyAsString(134217728L),
//                WritePairAsJavaBean(268435456L),
//                OptimizedForAscii(536870912L),
//                EscapeNoneAscii(1073741824L),
//                WriteByteArrayAsBase64(2147483648L),
//                IgnoreNonFieldGetter(4294967296L),
//                LargeObject(8589934592L),
//                WriteLongAsString(17179869184L),
//                BrowserSecure(34359738368L),
//                WriteEnumUsingOrdinal(68719476736L),
//                WriteThrowableClassName(137438953472L),
//                UnquoteFieldName(274877906944L),
//                NotWriteSetClassName(549755813888L),
//                NotWriteNumberClassName(1099511627776L);
//        fastJsonConfig.setSerializerFeatures(
//                SerializerFeature.WriteEnumUsingToString,
//                SerializerFeature.WriteNullStringAsEmpty,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteDateUseDateFormat,
//                SerializerFeature.DisableCircularReferenceDetect);
//        fastJsonConfig.setSerializeFilters((ValueFilter) (o, s, source) -> {
//            if (source == null) {
//                /*
//                 * 如果返回对象的变量为null,则自动变成""
//                 */
//                return "";
//            }
//            return source;
//        });
        return fastJsonConfig;
    }
}
