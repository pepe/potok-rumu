(ns potok-rumu.events
  (:require [potok.core :as ptk]
            [beicon.core :as rx]))
 
(defrecord ^:private GoToPub []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :state/in-pub? true)))

(defrecord ^:private LeavePub []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :state/in-pub? false)))

(defrecord ^:private Drink [^Number volume]
  ptk/UpdateEvent
  (update [_ state]
    (update state :state/potok (fnil + 0) volume)))

(defrecord ^:private SmallShot []
  ptk/WatchEvent
  (watch [_ _ _]
    (rx/just (->Drink 2))))

(defrecord ^:private BigShot []
  ptk/WatchEvent
  (watch [_ _ _]
    (rx/just (->Drink 5))))

(defrecord ^:private Drain []
  ptk/UpdateEvent
  (update [_ state]
    (assoc state :state/potok 0)))

(defrecord ^:private Sleep []
  ptk/EffectEvent
  (effect [_ _ _]
    (js/window.setTimeout #(js/alert "Psssst, he is sleeping!") 2500)))

(defrecord ^:private GoHome []
  ptk/WatchEvent
  (watch [_ state stream]
    (rx/merge
     (rx/just (->LeavePub))
     (rx/just (->Drain))
     (rx/just(->Sleep)))))
