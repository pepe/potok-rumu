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
    (let [ev (sut/->Drink 1)]
      (is (= (:state/potok (ptk/update ev initial-state)) 1))
      (is (state-valid? (ptk/update ev initial-state)))))
  (testing "Small Shot"
    (let [ev (sut/->SmallShot)]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(is (= (sut/->Drink 2) %)))))
  (testing "Big Shot"
    (let [ev (sut/->BigShot)]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(is (= (sut/->Drink 5) %)))))
  (testing "Drain"
    (let [ev (sut/->Drain)]
      (is (=  (:state/potok (ptk/update ev initial-state)) 0))
      (is (state-valid? (ptk/update ev initial-state))))))

(deftest test-pub-presence
  (testing "Go To Pub"
    (let [ev (sut/->GoToPub)]
      (is (= (:state/in-pub? (ptk/update ev initial-state)) true))
      (is (state-valid? (ptk/update ev initial-state)))))
  (testing "Leave Pub"
    (let [ev (sut/->LeavePub)]
      (is (= (:state/in-pub? (ptk/update ev initial-state)) false))
      (is (state-valid? (ptk/update ev initial-state)))))
  (testing "Go Home"
    (let [ev (sut/->GoHome)
          evs (atom #{})]
      (rx/on-value (ptk/watch ev initial-state (rx/empty))
                    #(swap! evs conj %))
      (is (= (count @evs) 3))
      (is (@evs (sut/->LeavePub)))
      (is (@evs (sut/->Drain)))
      (is (@evs (sut/->DelayedSleep))))))
