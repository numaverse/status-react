(ns status-im.ui.components.action-sheet
  (:require [status-im.i18n :as i18n]
            [status-im.utils.core :as utils]
            [status-im.react-native.js-dependencies :as js-dependencies]))

(defn- callback [options]
  (fn [index]
    (when (< index (count options))
      (when-let [handler (:action (nth options index))]
        (handler)))))

(defn- prepare-options [title message options]
  (let [destructive-opt-index (utils/first-index :destructive? options)] ;; TODO Can only be a single destructive?
    (clj->js (merge {:options           (conj (mapv :label options) (i18n/label :t/cancel))
                     :cancelButtonIndex (count options)}
                    (when destructive-opt-index
                      {:destructiveButtonIndex destructive-opt-index})
                    (when title {:title title})
                    (when message {:message message})))))

(defn show [{:keys [title message options]}]
  (.showActionSheetWithOptions (.-ActionSheetIOS js-dependencies/react-native)
                               (prepare-options title message options)
                               (callback options)))
