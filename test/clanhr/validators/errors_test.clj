(ns clanhr.validators.errors-test
  (use clojure.test)
  (require [clanhr.validators.errors :as errors]))

(deftest make-errors-friendly-test
  (let [processed (errors/make-errors-friendly {[:some :property] "failed"})]
    (is (get processed "some.property"))))

(deftest make-errors-friendly-in-test
  (let [data {:user {[:some :property] "failed"}}
        processed (errors/make-errors-friendly-in data :user)]
    (is (get-in processed [:user "some.property"]))))
