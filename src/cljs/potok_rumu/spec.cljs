(ns potok-rumu.spec
  (:require
   [cljs.spec :as s]))

(s/def :state/potok number?)
(s/def :state/in-pub? boolean?)

(s/def ::state (s/keys :req [:state/potok :state/in-pub?]))
