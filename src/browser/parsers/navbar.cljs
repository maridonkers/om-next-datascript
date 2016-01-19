(ns browser.parsers.navbar
  (:require [om.next :as om]
            [datascript.core :as d]

            [browser.reconciler :refer [mutate read]]))

(defmethod read :navbar/query
  [{:keys [state query]} _ _]

  {:value (d/q '[:find [(pull ?e ?selector) ...]
                 :in $ ?selector
                 :where [?e :navbar/key]]
               (d/db state) query)})
