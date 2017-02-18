(ns potok-rumu.views
  (:require
   [rum.core :as rum]
   [potok-rumu.events :refer [emit-to!]]
   [potok-rumu.state :refer [potok]]))

(rum/defc main < rum/reactive [store]
  [:div
   {:style
    {:width "90vw"
     :padding "0 5vw"
     :font-family "Roboto"}}
   [:h1 "Potok Rumu teče"]
   [:p
    (rum/react potok)
    " dl Rumu vypito"]
   [:button {:on-click #(emit-to! store :small-shot)} "Malý panák"]
   [:button {:on-click #(emit-to! store :big-shot)} "Velký panák"]
   [:button {:on-click #(emit-to! store :go-home)} "Jdi spát"]])

