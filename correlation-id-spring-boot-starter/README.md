# Spring Request Correlation ID

> A Spring Cloud starter for easy setup request correlation

## Features

* Correlation ID can be generated or provided as Request Header
* Correlation ID will be added to SLF4J MDC context parameter
* Correlation ID can be added as Response Header
* Servlet and Reactive applications supported

## Properties

You can configure following options:

```yaml
ru.sadv1r.cloud.correlation:
  # Use received correlation ID HTTP header if exists
  acceptRequestHeader: true
  # Add correlation ID to response HTTP headers
  addToResponse: true
  # HTTP correlation ID header name
  httpHeaderName: X-Request-Id
  # MDC context key name
  mdcKeyName: requestId
  # Priority order of the filter
  filterOrder: Ordered.HIGHEST_PRECEDENCE
```