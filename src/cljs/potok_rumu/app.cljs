(ns potok-rumu.app
  (:require
   [rum.core :as rum]
   [beicon.core :as rx]
   [potok.core :as ptk]
   [potok-rumu.store :as store]
   [potok-rumu.views :as views]))

(defn init []
  (rum/mount (views/main store/main)
             (. js/document (getElementById "container"))))

(enable-console-print!)
(defonce statelog (rx/subscribe store/main
                       #(println "New state:" %)
                       #(println "New error:" %)
                       #(println "Potok terminated.")))
