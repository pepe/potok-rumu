(ns potok-rumu.events-test
  (:require-macros [cljs.test :refer [deftest testing is are]])
  (:require [potok-rumu.events :as sut]
            [potok-rumu.spec]
            [potok-rumu.store]
            [potok.core :as ptk]
            [beicon.core :as rx]
            [cljs.spec :as s]
            [cljs.test :as t :include-macros true]))

(defonce initial-state (:state potok-rumu.store/initial-state))

(defn state-valid? [state]
  (is (s/valid? :potok-rumu.spec/state state)))

(deftest test-drinks []
  (testing "Drink"
    (let [ev (sut/->Drink 1)
          state (ptk/update ev initial-state)]
      (is (= (:state/potok state) 1))
      (is (state-valid? state))))
  (testing "Small Shot"
    (let [ev (sut/->SmallShot)]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(is (= (sut/->Drink 2) %)))))
  (testing "Big Shot"
    (let [ev (sut/->BigShot)]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(is (= (sut/->Drink 5) %)))))
  (testing "Drain"
    (let [ev (sut/->Drain)
          state (ptk/update ev initial-state)]
      (is (=  (:state/potok state) 0))
      (is (state-valid? state)))))

(deftest test-pub-presence
  (testing "Go To Pub"
    (let [ev (sut/->GoToPub)
          state (ptk/update ev initial-state)]
      (is (= (:state/in-pub? state) true))
      (is (state-valid? state))))
  (testing "Leave Pub"
    (let [ev (sut/->LeavePub)
          state (ptk/update ev initial-state)]
      (is (= (:state/in-pub? state) false))
      (is (state-valid? state))))
  (testing "Go Home"
    (let [ev (sut/->GoHome)
          evs (atom #{})]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(swap! evs conj %))
      (is (= (count @evs) 3))
      (is (@evs (sut/->LeavePub)))
      (is (@evs (sut/->Drain)))
      (is (@evs (sut/->DelayedSleep))))))
