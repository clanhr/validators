(ns clanhr.validators.core-test
  (use clojure.test)
  (require [clanhr.validators.core :as validate]
           [clj-time.coerce :as c]))

(deftest valid-date-test
  (is (validate/valid-date? "2015-03-03T00:00:00.000Z"))
  (is (not (validate/valid-date? "")))
  (is (not (validate/valid-date? nil)))
  (is (not (validate/valid-date? "hello"))))

(deftest date-validator-test
  (let [validator (validate/date-validator :date)]
    (is (first (validator {:date "2015-03-03T00:00:00.000Z"})))
    (is (first (validator {:date (c/from-string "2015-03-03T00:00:00.000Z")})))
    (is (first (validator {:date (c/to-sql-date "2015-03-03T00:00:00.000Z")})))
    (is (first (validator {:date (c/to-date "2015-03-03T00:00:00.000Z")})))
    (is (not (first (validator {:date ""}))))))

(deftest email-validator-test
  (let [validator (validate/email-validator :email)]
    (is (first (validator {:email "donbonifacio@gmail.com"})))
    (is (not (first (validator {:email "hello"}))))))

(deftest valid-currency-test
  (is (validate/valid-currency? "100"))
  (is (validate/valid-currency? "100.2"))
  (is (not (validate/valid-date? "")))
  (is (not (validate/valid-date? nil)))
  (is (not (validate/valid-date? "hello"))))

(deftest currency-validator-test
  (let [validator (validate/currency-validator :currency)]
    (is (first (validator {:currency "100.1"})))
    (is (not (first (validator {:currency "100.1/mês"}))))
    (is (not (first (validator {:currency "hello"}))))))

(deftest presence-of-if-test
  (let [validator-req (validate/presence-of-if :value (constantly true))
        validator-not-req (validate/presence-of-if :value (constantly false))]
    (is (not (first (validator-req {}))))
    (is (first (validator-not-req {})))))

