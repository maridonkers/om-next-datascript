(ns browser.parsers.about
  (:require [om.next :as om]
            [datascript.core :as d]

            [browser.reconciler :refer [mutate read]]))

;;-----------------------
;; Parser read functions.
(defmethod read :about/query
  [{:keys [state query]} _ _]

  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :about/key]]
               (d/db state) query)})

;;-------------------------
;; Parser mutate functions.
