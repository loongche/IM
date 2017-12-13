package com.shine.mobile.push.frags.main;


import com.shine.mobile.common.app.Fragment;
import com.shine.mobile.R;
import com.shine.mobile.common.widget.GalleryView;

import butterknife.BindView;

/**
 * @author Chenglz
 * @version 1.0.0
 */
public class ActiveFragment extends Fragment {
    @BindView(R.id.galleyView)
    GalleryView mGalley;
    public ActiveFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_active;
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
