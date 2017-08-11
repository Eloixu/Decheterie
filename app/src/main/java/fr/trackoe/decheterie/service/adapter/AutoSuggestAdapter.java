package fr.trackoe.decheterie.service.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Remi on 19/05/2016.
 */
public class AutoSuggestAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<String> items;
    private List<String> tempItems;
    private List<String> suggestions;

    public AutoSuggestAdapter(Context ctx, int resource, List<String> items) {
        super(ctx, resource, 0, items);
        this.context = ctx;
        this.resource = resource;
        this.items = items;
        tempItems = new ArrayList<String>(items);
        suggestions = new ArrayList<String>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, parent, false);
        }

        String item = items.get(position);

        if (item != null && view instanceof TextView) {
            ((TextView) view).setText(item);
        }

        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = (String) resultValue;
            return str;
        }

        @Override
        protected android.widget.Filter.FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String names : tempItems) {
                    if (names.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(names);
                    }
                }
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new android.widget.Filter.FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, android.widget.Filter.FilterResults results) {
            List<String> filterList = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();

                try {
                    for (String item : filterList) {
                        add(item);
                        notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
