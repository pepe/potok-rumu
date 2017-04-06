(ns potok-rumu.events-test
  (:require-macros [cljs.test :refer [deftest testing is are]])
  (:require [potok-rumu.events :as sut]
            [potok-rumu.spec]
            [potok.core :as ptk]
            [beicon.core :as rxt]
            [cljs.spec :as s]
            [cljs.test :as t :include-macros true]))

(defonce initial-state {:state/potok 0 :state/in-pub? false})

(defn valid-state? [state]
  (is (s/valid? :potok-rumu.spec/state state)))

(deftest test-drinks []
  (testing "Drink"
    (let [ev (sut/->Drink 1)]
      (is (= (:state/potok (ptk/update ev initial-state)) 1))
      (is (valid-state? (ptk/update ev initial-state)))))
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
      (is (=  (:state/potok (ptk/update ev initial-state)) 0))
      (is (valid-state? (ptk/update ev initial-state))))))

(deftest test-pub-presence
  (testing "Go To Pub"
    (let [ev (sut/->GoToPub)]
      (is (= (:state/in-pub? (ptk/update ev initial-state)) true))
      (is (valid-state? (ptk/update ev initial-state)))))
  (testing "Leave Pub"
    (let [ev (sut/->LeavePub)]
      (is (= (:state/in-pub? (ptk/update ev initial-state)) false))
      (is (valid-state? (ptk/update ev initial-state)))))
  (testing "Go Home"
    (let [ev (sut/->GoHome)
          evs (atom #{})]
      (rxt/on-value (ptk/watch ev initial-state (rxt/empty))
                    #(swap! evs conj %))
      (is (= (count @evs) 3))
      (is (@evs (sut/->LeavePub)))
      (is (@evs (sut/->Drain)))
      (is (@evs (sut/->Sleep))))))
