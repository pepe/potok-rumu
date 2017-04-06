(ns potok-rumu.events-test
  (:require-macros [cljs.test :refer [deftest testing is are]])
  (:require [potok-rumu.events :as sut]
            [potok.core :as ptk]
            [beicon.core :as rxt]
            [cljs.test :as t :include-macros true]))
(defonce init-state {:potok 0})

(deftest test-drinks []
  (testing "Drink"
    (let [ev (sut/->Drink 1)]
      (is (= (ptk/update ev init-state) {:potok 1}))))
  (testing "Small Shot"
    (let [ev (sut/->SmallShot)]
      (rxt/on-value (ptk/watch ev init-state (rxt/empty))
                    #(is (= (sut/->Drink 0.2) %)))))
  (testing "Big Shot"
    (let [ev (sut/->BigShot)]
      (rxt/on-value (ptk/watch ev init-state (rxt/empty))
                    #(is (= (sut/->Drink 0.5) %)))))
  (testing "Drain"
    (let [ev (sut/->Drain)]
      (is (= (ptk/update ev init-state) {:potok 0})))))
