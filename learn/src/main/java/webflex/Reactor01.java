package webflex;

import reactor.core.publisher.Flux;

public class Reactor01 {
    public static void main(String[] args) {
        Flux.create(sink -> {
                    for (int i = 0; i < 10; i++) {
                        sink.next("hello:" + i);
                    }
                })
                .buffer(2)
                .log()
                .subscribe();
        Flux.just(1, 2, 3)
                .buffer(2)
                .log()
                .subscribe();
    }
}
