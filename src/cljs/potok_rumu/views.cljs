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
         [:h1 "Potok Rumu"]
         [:p
          (rum/react potok)
          " dl of Rumu drank"]
         (emitting-button store (->SmallShot) "Small shot")
         (emitting-button store (->BigShot) "Big shot")
         (emitting-button store (->GoHome) "Go home")]))

