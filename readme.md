### compile + run backend

```shell
sbt "backend/clean ; backend/run"
```

### compile frontend

```shell
sbt "frontend/clean ; frontend/fastLinkJS/webpack"
```

### open frontend in the browser

```shell
open frontend/target/scala-2.13/classes/index-dev.html
```
