(ns potok-rumu.views
  (:require
   [rum.core :refer [defc reactive cursor react]]
   [potok.core :refer [emit!]]
   [beicon.core :refer [to-atom]]
   [potok-rumu.events :refer [->GoToPub ->BigShot ->SmallShot ->GoHome]]))

(defc emit-button
  [^BehaviorSubject store event ^String label]
  [:button {:on-click #(emit! store event)} label])

(defc main < reactive
  [^BehaviorSubject store]
  (let [state (to-atom store)
        potok (cursor state :state/potok)
        in-pub? (cursor state :state/in-pub?)]
    [:div
     {:style
      {:width "90vw"
       :padding "0 5vw"
       :font-family "Roboto"}}
     [:h1 "Potok Rumu"]
     [:p
      (react potok)
      " cl of Rumu drank"]
     (if (react in-pub?)
       [:div
        (emit-button store (->SmallShot) "Small shot")
        (emit-button store (->BigShot) "Big shot")
        (emit-button store (->GoHome) "Go home")]
       [:div
        (emit-button store (->GoToPub) "Go to pub")])]))
