(ns potok-rumu.events-test
  (:require-macros [cljs.test :refer [deftest testing is are]])
  (:require [potok-rumu.store :refer [initial-state]]
            [potok-rumu.events :as sut]
            [potok.core :as ptk]
            [beicon.core :as rxt]
            [cljs.test :as t :include-macros true]))

(deftest test-drinks []
  (testing "Drink"
    (let [ev (sut/->Drink 1)]
      (is (= (:potok (ptk/update ev initial-state)) 1))))
  (testing "Small Shot"
    (let [ev (sut/->SmallShot)]
      (rxt/on-value (ptk/watch ev initial-state (rxt/empty))
                    #(is (= (sut/->Drink 2) %)))))
  (testing "Big Shot"
    (let [ev (sut/->BigShot)]
      (rxt/on-value (ptk/watch ev initial-state (rxt/empty))
                    #(is (= (sut/->Drink 5) %)))))
  (testing "Drain"
    (let [ev (sut/->Drain)]
      (is (=  (:potok (ptk/update ev initial-state)) 0)))))

(deftest test-pub-presence
  (testing "Go To Pub"
    (let [ev (sut/->GoToPub)]
      (is (= (:in-pub? (ptk/update ev initial-state)) true))))
  (testing "Leave Pub"
    (let [ev (sut/->LeavePub)]
      (is (= (:in-pub? (ptk/update ev initial-state)) false))))
  (testing "Go Home"
    (let [ev (sut/->GoHome)
          evs (atom #{})]
      (rxt/on-value (ptk/watch ev initial-state (rxt/empty))
                    #(swap! evs conj %))
      (is (= (count @evs) 3))
      (is (@evs (sut/->Drain)))
      (is (@evs (sut/->LeavePub)))
      (is (@evs (sut/->Sleep))))))
