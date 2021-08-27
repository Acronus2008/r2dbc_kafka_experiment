package com.acl.r2oracle.kafka.experiments.service.xml.contract;

import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.XMLDeltaTO;
import com.acl.r2oracle.kafka.experiments.service.xml.schema.InventoryListType;
import com.acl.r2oracle.kafka.experiments.service.xml.schema.InventoryType;
import com.acl.r2oracle.kafka.experiments.service.xml.schema.RecordType;
import com.acl.r2oracle.kafka.experiments.util.date.DateUtil;
import com.acl.r2oracle.kafka.experiments.util.json.JsonSerdes;
import com.acl.r2oracle.kafka.experiments.service.xml.contract.to.ItemTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Mapper(imports = {
        DateUtil.class,
        String.class,
        BigInteger.class
})
public interface XMLBinder {
    XMLBinder XML_BINDER = Mappers.getMapper(XMLBinder.class);

    BiFunction<List<ItemTO>, List<ItemTO>, List<ItemTO>> reduce = (l1, l2) -> {
        l1.addAll(l2);
        return l1;
    };

    default XMLDeltaTO bind(InventoryType source) {
        List<ItemTO> items = source.getInventoryList()
                .stream()
                .map(this::bindItem)
                .reduce(this.reduce::apply)
                .orElse(new ArrayList<>());
        return new XMLDeltaTO()
                .withItems(items);
    }

    default List<ItemTO> bindItem(InventoryListType source) {
        return source.getRecords()
                .getRecord()
                .stream()
                .map(this::bindRecord)
                .collect(Collectors.toList());
    }

    @Mapping(target = "date", expression = "java(DateUtil.parseCalendarToString.apply(source.getAllocationTimestamp()))")
    @Mapping(target = "productId", expression = "java(String.valueOf(source.getProductId()))")
    @Mapping(target = "stock", expression = "java(new BigInteger(new byte[]{source.getAllocation()}).intValue())")
    ItemTO bindRecord(RecordType source);

    default ItemTO bind(Map source) {
        return JsonSerdes.convert(source, ItemTO.class);
    }
}
