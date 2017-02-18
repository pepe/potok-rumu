(ns potok-rumu.app
  (:require
   [rum.core :as rum]
   [beicon.core :as rx]
   [potok.core :as ptk]
   [potok-rumu.store :as store]
   [potok-rumu.views :as views]))

(defn init []
  (let [state (rx/to-atom store/main)]
    (rum/mount (views/main store/main state)
               (. js/document (getElementById "container")))))
