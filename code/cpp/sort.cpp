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

template <typename InputIt, typename OutputIt, typename Cmp>
void merge_sort(InputIt src, OutputIt dst, size_t size, bool orig, Cmp cmp) {
    if (size == 0u)
        return;
    if (size == 1u) {
        if (orig) // Moving from original collection to auxiliary array.
            *dst = std::move(*src);
        return;
    }
    auto half = size >> 1u;
    merge_sort(dst, src, half, !orig, cmp);
    merge_sort(dst + half, src + half, size - half, !orig, cmp);
    auto mid = src + half, end = src + size, l = src, r = mid;
    for (auto out = dst; l != mid || r != end; ++out) {
        if      (l == mid)       *out = std::move(*r++);
        else if (r == end)       *out = std::move(*l++);
        else if (cmp(*l, *r))    *out = std::move(*l++);
        else /* (cmp(*r, *l)) */ *out = std::move(*r++);
    }
}

template <typename It,
          typename Cmp = std::less<typename It::value_type>>
void merge_sort(It begin, It end, Cmp cmp = Cmp()) {
    auto size = distance(begin, end);
    auto aux = new typename It::value_type[size];
    merge_sort(aux, begin, size, /*orig*/ false, cmp);
    delete[] aux;
}

template <typename Sort>
void test(Sort custom_sort) {
    int n = 10000;
    std::vector<int> v(n);
    for (int i = 0; i < n; ++i)
        v[i] = i;
    random_shuffle(v.begin(), v.end());
    custom_sort(v.begin(), v.end());
    assert(is_sorted(v.begin(), v.end()));
    std::vector<bool> bitmap(n);
    for (auto i : v)
        bitmap[i] = true;
    for (auto b : bitmap) {
        assert(b);
    }
}

int main() {
    test([](auto begin, auto end) { selection_sort(begin, end); });
    test([](auto begin, auto end) { insertion_sort(begin, end); });
    test([](auto begin, auto end) { merge_sort(begin, end); });
    std::cout << "All tests passed" << std::endl;
    return 0;
}
