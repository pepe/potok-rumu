(ns potok-rumu.events
  (:require [potok.core :as ptk]
            [beicon.core :as rx]))

(deftype ^:private SmallShot []
  ptk/UpdateEvent
  (update [_ state]
    (update state :potok (fnil + 0) 0.2)))

(deftype ^:private BigShot []
  ptk/UpdateEvent
  (update [_ state]
    (update state :potok (fnil + 0) 0.5)))

(deftype ^:private Drain []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :potok 0.0)))

(deftype ^:private GoHome []
  ptk/WatchEvent
  (watch [_ state stream]
    (->> (rx/just (->Drain))
         (rx/delay 2000))))

(def ^:private events-map
  {:small-shot ->SmallShot
   :big-shot ->BigShot
   :go-home ->GoHome})

(defn emit-to!
  [store event]
  (ptk/emit! store ((event events-map))))

