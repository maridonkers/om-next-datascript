(ns browser.pages.about
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [datascript.core :as d]

            [browser.util :as util]

            [browser.parsers.about :as about-parser]))

;; ------------------------------------
;; Om Next component for the about page.
(defui AboutPage
  static om/IQuery
  (query [this]
         [:db/id :about/title])
  Object
  (render [this]
          (let [props (om/props this)

                {:keys [about/title]} props]
            
            (dom/div
             nil 
             (dom/h2 nil title)
             (dom/button
              #js {:type "button"
                   :onClick (fn [e]
                              (util/nav! "/"))}
              "HOME!")))))

(def about-page (om/factory AboutPage))
