#include <iostream>
#include <vector>
#include <cassert>

template <typename It,
          typename Cmp = std::less<typename It::value_type>>
void selection_sort(It begin, It end, Cmp cmp = Cmp()) {
    for (; begin != end; ++begin) {
        auto best = begin;
        for (auto it = next(best); it != end; ++it)
            if (cmp(*it, *best))
                best = it;
        if (best != begin) {
            std::iter_swap(begin, best);
        }
    }
}

template <typename It,
          typename Cmp = std::less<typename It::value_type>>
void insertion_sort(It begin, It end, Cmp cmp = Cmp()) {
    for (auto mid = begin; mid != end; ++mid) {
        for (auto it = mid; it != begin && cmp(*it, *prev(it)); --it) {
            std::iter_swap(it, prev(it));
        }
    }
}

template <typename It,
          typename Cmp = std::less<typename It::value_type>>
void merge_sort(It begin, It end, Cmp cmp,
                typename It::value_type aux[]) {
    auto size = distance(begin, end);
    if (size <= 1u)
        return;
    auto mid = begin + (size >> 1u);
    merge_sort(begin, mid, cmp, aux), merge_sort(mid, end, cmp, aux);
    auto aux_it = aux;
    for (auto l = begin, r = mid; l != mid || r != end; ++aux_it) {
        if      (l == mid)       *aux_it = std::move(*r++);
        else if (r == end)       *aux_it = std::move(*l++);
        else if (cmp(*l, *r))    *aux_it = std::move(*l++);
        else /* (cmp(*r, *l)) */ *aux_it = std::move(*r++);
    }
    for (auto aux_it = aux, e = aux + size; aux_it != e; ++aux_it) {
        *begin++ = std::move(*aux_it);
    }
}

template <typename It,
          typename Cmp = std::less<typename It::value_type>>
void merge_sort(It begin, It end, Cmp cmp = Cmp()) {
    auto aux = new typename It::value_type[distance(begin, end)];
    merge_sort(begin, end, cmp, aux);
    delete[] aux;
}

int main() {
    int n = 10000;
    std::vector<int> v(n);
    for (int i = 0; i < n; ++i) {
        v[i] = i;
    }

    random_shuffle(v.begin(), v.end());
    selection_sort(v.begin(), v.end());
    assert(is_sorted(v.begin(), v.end()));

    random_shuffle(v.begin(), v.end());
    insertion_sort(v.begin(), v.end());
    assert(is_sorted(v.begin(), v.end()));

    random_shuffle(v.begin(), v.end());
    merge_sort(v.begin(), v.end());
    assert(is_sorted(v.begin(), v.end()));

    std::cout << "All tests passed" << std::endl;
    return 0;
}
