package com.example.androidproject_coupon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidproject_coupon.CouponManagement.CouponFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new OrderFragment();
            case 2 :
                return new CouponFragment();
            case 3 :
                return new AccountFragment();
        }

        return new ProductFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
