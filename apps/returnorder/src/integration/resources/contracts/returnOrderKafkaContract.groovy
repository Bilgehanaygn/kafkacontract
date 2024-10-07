package contracts

import org.springframework.cloud.contract.spec.Contract;

Contract.make {
    description "Should publish ReturnOrderEvent into 'return-order' topic"

    label 'returnOrderEvent'

    input {
        triggeredBy('publishReturnOrderEvent()')
    }

    outputMessage {
        sentTo('return-order')
        body([
                orderId     : $(consumer(regex(uuid())), producer(anyUuid())),
                productId   : $(consumer(regex(uuid())), producer(anyUuid())),
                productName : $(consumer(regex('[A-Za-z0-9]{10,20}')), producer(regex('[A-Za-z0-9]{10,20}'))),
                price       : $(consumer(regex('[0-9]+(\\.[0-9]{1,2})?')), producer(anyDouble()))
        ])
    }
}

