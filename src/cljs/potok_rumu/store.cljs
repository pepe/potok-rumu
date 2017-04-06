(ns potok-rumu.store
  (:require [rum.core :as rum]
            [potok.core :as ptk]))


(defonce initial-state
  {:state {:state/potok 0 :state/in-pub? false}})

(defonce main
  (ptk/store initial-state))


