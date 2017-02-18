(ns potok-rumu.app
  (:require
   [rum.core :as rum]
   [potok.core :as ptk]
   [potok-rumu.state :refer [store]]
   [potok-rumu.views :as views]))

(defn init []
  (rum/mount (views/main store)
             (. js/document (getElementById "container"))))
