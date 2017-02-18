(ns potok-rumu.state
  (:require [rum.core :as rum]
            [potok.core :as ptk]
            [beicon.core :as rx]))

(defonce store (ptk/store {:state {:potok 0}}))

(def state (rx/to-atom store))

(def potok (rum/cursor-in state [:potok]))

