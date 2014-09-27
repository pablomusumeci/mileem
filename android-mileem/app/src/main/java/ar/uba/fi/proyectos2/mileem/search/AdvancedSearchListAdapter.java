package ar.uba.fi.proyectos2.mileem.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ar.uba.fi.proyectos2.mileem.R;

/**
 * Created by javier on 25/09/14.
 */
public class AdvancedSearchListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (groupPosition == 0 && convertView == null) {
            convertView = inflater.inflate(R.layout.advanced_search_empty, null);
        } else if (groupPosition == 1 && convertView == null) {
            convertView = inflater.inflate(R.layout.advanced_search_advanced_options, null);
        }
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (groupPosition == 0 && convertView == null) {
            convertView = inflater.inflate(R.layout.advanced_search_basic_options, null);
            // opciones por defecto
            RadioButton rb = (RadioButton) convertView.findViewById(R.id.radioButtonAmbas);
            rb.setChecked(true);
            RadioButton rbCurrency = (RadioButton) convertView.findViewById(R.id.radioButtonARS);
            rbCurrency.setChecked(true);
        } else if (groupPosition == 1 && convertView == null) {
            convertView = inflater.inflate(R.layout.advanced_search_advanced_options_title, null);
        }
        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return 2;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
