(ns potok-rumu.views
  (:require
   [rum.core :as rum]
   [potok.core :as ptk]
   [potok-rumu.events :refer [->BigShot ->SmallShot ->GoHome]]))

(rum/defc emitting-button
  [^BehaviorSubject store event ^String label]
  [:button {:on-click #(ptk/emit! store event)} label])

(rum/defc main < rum/reactive
  [^BehaviorSubject store ^Atom state]
  (let [potok (rum/cursor-in state [:potok])]
        [:div
         {:style
          {:width "90vw"
           :padding "0 5vw"
           :font-family "Roboto"}}
         [:h1 "Potok Rumu teče"]
         [:p
          (rum/react potok)
          " dl Rumu vypito"]
         (emitting-button store (->SmallShot) "Malý panák")
         (emitting-button store (->BigShot) "Velky panák")
         (emitting-button store (->GoHome) "Jdi spát")]))

