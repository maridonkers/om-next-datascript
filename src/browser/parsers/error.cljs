(ns browser.parsers.error
  (:require [om.next :as om]
            [datascript.core :as d]

            [browser.reconciler :refer [mutate read]]))

;;-----------------------
;; Parser read functions.
(defmethod read :error/query
  [{:keys [state query]} _ _]

  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :error/key]]
               (d/db state) query)})

;;-------------------------
;; Parser mutate functions.
