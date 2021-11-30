# queryable problems

Each filter definition requires a name that’s unique within your persistence unit #21
issue: https://github.com/n-essio/queryable/issues/21

### In our example

- User has field: public boolean active
  - with filterDef:
  - @FilterDef(name = "obj.active", parameters = @ParamDef(name = "active", type = "boolean"))
  - @Filter(name = "obj.active", condition = "active = :active")

- Area has field: public String active;
   - with filterDef:
   - @FilterDef(name = "obj.active", parameters = @ParamDef(name = "active", type = "string"))
   - @Filter(name = "obj.active", condition = "active = :active")

To test:
- docker-compose -f docker/docker-compose.yaml up
- mvn quarkus:dev

Open the browser link:
- http://localhost:8080/hello/init [to populate the db]
- http://localhost:8080/hello  [to view the error]

SOLUTIONS:
- queryable should generate @FilterDef(name = "obj.active") => @FilterDef(name = "Entity.obj.active")
- and in the method getSearch() => filter enabling should be: earch.filter("Entity.obj.code", Parameters.with("code", get("obj.code"))); 




The error that we have with same FilterDef name, but different type:

```
java.lang.IllegalArgumentException: Incorrect type for parameter [active]
at org.hibernate.internal.FilterImpl.setParameter(FilterImpl.java:83)
at io.quarkus.hibernate.orm.panache.common.runtime.CommonPanacheQueryImpl.applyFilters(CommonPanacheQueryImpl.java:366)
at io.quarkus.hibernate.orm.panache.common.runtime.CommonPanacheQueryImpl.list(CommonPanacheQueryImpl.java:240)
at io.quarkus.hibernate.orm.panache.runtime.PanacheQueryImpl.list(PanacheQueryImpl.java:149)
at dev.queryable.filterdef.issue.service.rs.GreetingResource.hello(GreetingResource.java:76)
at dev.queryable.filterdef.issue.service.rs.GreetingResource_Subclass.hello$$superforward1(GreetingResource_Subclass.zig:202)
at dev.queryable.filterdef.issue.service.rs.GreetingResource_Subclass$$function$$1.apply(GreetingResource_Subclass$$function$$1.zig:24)
at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:54)
at io.quarkus.arc.runtime.devconsole.InvocationInterceptor.proceed(InvocationInterceptor.java:62)
at io.quarkus.arc.runtime.devconsole.InvocationInterceptor.monitor(InvocationInterceptor.java:49)
at io.quarkus.arc.runtime.devconsole.InvocationInterceptor_Bean.intercept(InvocationInterceptor_Bean.zig:516)
at io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:41)
at io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:50)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:132)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:103)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:38)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:57)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:32)
at io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(TransactionalInterceptorRequired_Bean.zig:335)
at io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:41)
at io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:41)
at io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:32)
at dev.queryable.filterdef.issue.service.rs.GreetingResource_Subclass.hello(GreetingResource_Subclass.zig:286)
at dev.queryable.filterdef.issue.service.rs.GreetingResource_ClientProxy.hello(GreetingResource_ClientProxy.zig:155)
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
at java.base/java.lang.reflect.Method.invoke(Method.java:566)
at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:170)
at org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:130)
at org.jboss.resteasy.core.ResourceMethodInvoker.internalInvokeOnTarget(ResourceMethodInvoker.java:660)
at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTargetAfterFilter(ResourceMethodInvoker.java:524)
at org.jboss.resteasy.core.ResourceMethodInvoker.lambda$invokeOnTarget$2(ResourceMethodInvoker.java:474)
at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:364)
at org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTarget(ResourceMethodInvoker.java:476)
at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:434)
at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:408)
at org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:69)
at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:492)
at org.jboss.resteasy.core.SynchronousDispatcher.lambda$invoke$4(SynchronousDispatcher.java:261)
at org.jboss.resteasy.core.SynchronousDispatcher.lambda$preprocess$0(SynchronousDispatcher.java:161)
at org.jboss.resteasy.core.interception.jaxrs.PreMatchContainerRequestContext.filter(PreMatchContainerRequestContext.java:364)
at org.jboss.resteasy.core.SynchronousDispatcher.preprocess(SynchronousDispatcher.java:164)
at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:247)
at io.quarkus.resteasy.runtime.standalone.RequestDispatcher.service(RequestDispatcher.java:73)
at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler.dispatch(VertxRequestHandler.java:135)
at io.quarkus.resteasy.runtime.standalone.VertxRequestHandler$1.run(VertxRequestHandler.java:90)
at io.quarkus.vertx.core.runtime.VertxCoreRecorder$13.runWith(VertxCoreRecorder.java:543)
at org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2449)
at org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1478)
at org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)
at org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)
at io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)
at java.base/java.lang.Thread.run(Thread.java:829)
Resulted in: org.jboss.resteasy.spi.UnhandledException: java.lang.IllegalArgumentException: Incorrect type for parameter [active]
at org.jboss.resteasy.core.ExceptionHandler.handleApplicationException(ExceptionHandler.java:105)
at org.jboss.resteasy.core.ExceptionHandler.handleException(ExceptionHandler.java:359)
at org.jboss.resteasy.core.SynchronousDispatcher.writeException(SynchronousDispatcher.java:218)
at org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:519)
... 15 more
```