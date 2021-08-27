package com.acl.r2oracle.kafka.experiments.service.xml.contract.to;

import com.acl.r2oracle.kafka.experiments.core.TransferObject;

import java.util.List;

public class XMLDeltaTO extends TransferObject {
    private List<ItemTO> items;

    public List<ItemTO> getItems() {
        return items;
    }

    public void setItems(List<ItemTO> items) {
        this.items = items;
    }

    public XMLDeltaTO withItems(List<ItemTO> items) {
        this.setItems(items);
        return this;
    }
}
