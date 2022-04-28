package com.happy.todo.calendarview;

public enum CalendarSelectionMode {
        /**
         * Only one date will be selectable.  If there is already a selected date and you select a new
         * one, the old date will be unselected.
         */
        SINGLE,
        /**
         * Multiple dates will be selectable.  Selecting an already-selected date will un-select it.
         */
        MULTIPLE,
        /**
         * Allows you to select a date range.  Previous selections are cleared when you either:
         * <ul>
         * <li>Have a range selected and select another date (even if it's in the current range).</li>
         * <li>Have one date selected and then select an earlier date.</li>
         * </ul>
         */
        RANGE
    }
