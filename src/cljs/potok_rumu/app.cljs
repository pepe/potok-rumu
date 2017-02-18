(ns potok-rumu.app
  (:require
   [rum.core :as rum]
   [potok.core :as ptk]
   [potok-rumu.events :refer [BigShot SmallShot GoHome]]
   [potok-rumu.state :refer [store potok]]))

(rum/defc label < rum/reactive []
  [:div
   [:h1 "Potok Rumu teče"]
   [:p
    (rum/react potok)
    " dl Rumu vypito"]
   [:button {:on-click #(ptk/emit! store (SmallShot.))} "Malý panák"]
   [:button {:on-click #(ptk/emit! store (BigShot.))} "Velký panák"]
   [:button {:on-click #(ptk/emit! store (GoHome.))} "Jdi spát"]])

(defn init []
  (rum/mount (label) (. js/document (getElementById "container"))))
