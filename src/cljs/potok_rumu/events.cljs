(ns potok-rumu.events
  (:require [potok.core :as ptk]
            [beicon.core :as rx]))

(defrecord ^:private Drink [^Number volume]
  ptk/UpdateEvent
  (update [_ state]
    (update state :potok (fnil + 0) volume)))

(deftype ^:private SmallShot []
  ptk/WatchEvent
  (watch [_ _ _]
    (rx/just (->Drink 0.2))))

(deftype ^:private BigShot []
  ptk/WatchEvent
  (watch [_ _ _]
    (rx/just (->Drink 0.5))))

(deftype ^:private Drain []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :potok 0.0)))

(deftype ^:private Sleep []
  ptk/EffectEvent
  (effect [_ _ _]
    (js/alert "Pššššš, už spí!")))

(deftype ^:private GoHome []
  ptk/WatchEvent
  (watch [_ state stream]
    (rx/merge
     (->> (rx/just (->Drain))
          (rx/delay 2000))
     (->> (rx/just(->Sleep))
          (rx/delay 2500)))))
