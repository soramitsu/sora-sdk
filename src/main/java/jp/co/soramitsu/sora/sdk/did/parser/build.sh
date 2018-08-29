#!/bin/bash -e

cd $(dirname $0)

package=jp.co.soramitsu.sora.did.parser.generated
rm generated/*.java || true
java -cp aparse-2.5.jar com.parse2.aparse.Parser -destdir generated -language java -package $package did.abnf
