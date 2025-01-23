const Redis = require("ioredis");

const cluster = new Redis.Cluster([
    { host: "172.16.8.70", port: 6080 },
    // { host: "127.0.0.1", port: 7001 },
]);

cluster.set("key", "zhangsan").then(() => {
    return cluster.get("key");
}).then((result) => {
    console.log(result);
});
