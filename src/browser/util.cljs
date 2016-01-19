(ns browser.util)

(defn nav!
  "Navigates to supplied page by updating the URL."
  [url]
  (set! (.. js/document -location -href) (str "#" url)))
