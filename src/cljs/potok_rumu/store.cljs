(ns potok-rumu.store
  (:require [rum.core :as rum]
            [potok.core :as ptk]))


(defonce ^:private initial-state
  {:state {:potok 0}})

(defonce main
  (ptk/store initial-state))


