(ns potok-rumu.views
  (:require
   [rum.core :as rum]
   [potok-rumu.events :refer [emit-to!]]))

(rum/defc emitting-button
  [^BehaviorSubject store ^Keyword event ^String label]
  [:button {:on-click #(emit-to! store event)} label])

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
         (emitting-button store :small-shot "Malý panák")
         (emitting-button store :small-shot "Velky panák")
         (emitting-button store :go-home "Jdi spát")]))

