#include <iostream>
#include <vector>
#include <unordered_set>
#include <cassert>

template <typename T,
          typename Hash = std::hash<T>,
          typename Cmp = std::equal_to<T>>
class hash_set {
    using hash_table = std::vector<std::vector<T>>;
    static constexpr float load_threshold = 1.0f;
    Hash hash;
    Cmp cmp;
    hash_table table;
    size_t size;

    inline size_t mask() {
        return table.size() - 1u;
    }

    inline size_t idx(const T& data) {
        return hash(data) & mask();
    }

    void ensure_capacity() {
        if (static_cast<float>(size + 1) / table.size() > load_threshold) {
            hash_table tmp(table.size() << 1);
            swap(table, tmp);
            for (auto& v : tmp) {
                for (auto& data : v) {
                    table[idx(data)].push_back(std::move(data));
                }
            }
        }
    }

public:
    hash_set(Hash hash = Hash(), Cmp cmp = Cmp())
        : hash(hash), cmp(cmp), table(1u) {}

    void insert(T data) {
        ensure_capacity();
        auto& v = table[idx(data)];
        for (auto& elem : v)
            if (elem == data)
                return;
        v.push_back(data);
        ++size;
    }

    bool contains(const T& data) {
        for (const auto& elem : table[idx(data)])
            if (elem == data)
                return true;
        return false;
    }

    void remove(const T& data) {
        auto& v = table[idx(data)];
        auto it = find(v.begin(), v.end(), data);
        if (it != v.end()) {
            v.erase(it);
            --size;
        }
    }
};

int main() {
    hash_set<int> ours;
    std::unordered_set<int> theirs;
    int op_counts[] = {0, 0, 0};
    for (int i = 0; i < 1000000; ++i) {
        int data = rand() % 100;
        int op = rand() % 3;
        ++op_counts[op];
        if (op == 0) {
            assert(ours.contains(data) == theirs.count(data));
        } else if (op == 1) {
            ours.insert(data);
            theirs.insert(data);
        } else {
            assert(op == 2);
            ours.remove(data);
            theirs.erase(data);
        }
    }

    for (int n : op_counts)
        assert(n > 0);
    std::cout << "All tests passed" << std::endl;
    return 0;
}
