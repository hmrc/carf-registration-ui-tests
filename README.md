# carf-registration-ui-tests
`carf-registration-frontend` UI journey tests.

## Pre-requisites
The relevant microservices should be up and running for the registration ui journey tests

### Services

Start Mongo Docker container as follows:

```bash
docker run --rm -d -p 27017:27017 --name mongo percona/percona-server-mongodb:5.0
```

Start `carf-registration-frontend` services as follows:

```bash
sm2 --start CARF_ALL
```
Starting a large group of services in a profile can overload the cpu of a machine and lead to services failing to start.
If this happens use one, or a combination of the following arguments: `--delay-seconds 5` to include a delay of 5
seconds between sm2 starting each service and `--workers 1` to force sm2 to only start one service at a time.

### Selenium Manager

Confirm that ui-test-runner (https://github.com/hmrc/ui-test-runner) is up-to-date and follow the provided [instructions](https://github.com/hmrc/ui-test-runner/blob/main/README.md).


### Test inspection and debugging

The system property `browser.option.headless` is defaulted to `true` in ui-test-runner. To view tests running locally in the browser UI for debugging, set the system property `-Dbrowser.option.headless=false` in the sbt command.

## Tests

Run tests as follows:

* Argument `<browser>` must be `chrome`, `edge`, or `firefox`.
* Argument `<environment>` must be `local`, `dev`, `qa` or `staging`.

```bash
./run_tests.sh <browser> <environment>
```

### Running ZAP tests

ZAP tests can be automated using the HMRC Dynamic Application Security Testing approach. Running
automated ZAP tests should not be considered a substitute for manual exploratory testing using OWASP ZAP.

#### Executing a local ZAP test

First [run the DAST tool locally](https://github.com/hmrc/dast-config-manager/blob/main/README.md#running-zap-locally)

The shell script `run_local_zap_tests.sh` is available to execute ZAP tests. The script proxies the journeys tagged
with 'ZapTests' via ZAP.

For example, to execute ZAP tests locally using Chrome browser:

```
./run_local_zap_test.sh chrome local
```


## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
