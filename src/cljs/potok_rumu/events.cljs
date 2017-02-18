(ns potok-rumu.events
  (:require [potok.core :as ptk]
            [beicon.core :as rx]))

(deftype SmallShot []
  ptk/UpdateEvent
  (update [_ state]
    (update state :potok (fnil + 0) 0.2)))

(deftype BigShot []
  ptk/UpdateEvent
  (update [_ state]
    (update state :potok (fnil + 0) 0.5)))

(deftype ^:private Drain []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :potok 0.0)))

(deftype GoHome []
  ptk/WatchEvent
  (watch [_ state stream]
    (->> (rx/just (->Drain))
         (rx/delay 2000))))

