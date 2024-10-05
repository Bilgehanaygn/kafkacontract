package contracts

import org.springframework.cloud.contract.spec.Contract;

Contract.make {
    description "Should publish ReturnOrderEvent into 'return-order' topic"

    label 'returnOrderEvent'

    input {
        triggeredBy('create()')
    }

    outputMessage {
        sentTo('return-order')
        headers {
            header('contentType', 'application/json')
        }
        body([
                orderId     : $(consumer(regex(uuid())), producer(anyUuid())),
                productId   : $(consumer(regex(uuid())), producer(anyUuid())),
                productName : $(consumer(nonEmpty()), producer("Sample Product")),
                price       : $(consumer(regex('[0-9]+(\\.[0-9]{1,2})?')), producer("99.99"))
        ])
    }
}