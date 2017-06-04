#include <iostream>
#include <set>
#include <cassert>

template <typename T>
class tree {

    struct node {
        const T data;
        node *left = nullptr, *right = nullptr, *parent;
        node(T data, node* parent): data(data), parent(parent) {}
    };

    struct iterator {
        node* n;
        iterator(node* n): n(n) {}
        bool operator==(iterator o) const { return n == o.n; }
        bool operator!=(iterator o) const { return n != o.n; }
        const T& operator*() const { return n->data; }
        const T* operator->() const { return &n->data; }
        iterator operator++(int) { auto copy = *this; ++(*this); return copy; }
        iterator& operator++() {
            if (n->right != nullptr) {
                n = n->right;
                while (n->left != nullptr) {
                    n = n->left;
                }
            } else {
                while (n->parent != nullptr && n == n->parent->right)
                    n = n->parent;
                n = n->parent;
            }
            return *this;
        }
    };

    node* root = nullptr;

public:
    bool contains(const T& data) const {
        auto n = root;
        while (n != nullptr) {
            if (n->data == data)
                return true;
            n = data < n->data ? n->left : n->right;
        }
        return false;
    }

    void insert(T data) {
        node* parent = nullptr;
        auto ptr = &root;
        while (auto n = *ptr) {
            if (n->data == data)
                return;
            parent = n;
            ptr = data < n->data ? &n->left : &n->right;
        }
        *ptr = new node(data, parent);
    }

    void remove(const T& data) {
        node* parent = nullptr;
        auto ptr = &root;
        while (auto n = *ptr) {
            if (data == n->data) {
                if (n->left == nullptr && n->right == nullptr) {
                    *ptr = n->right;
                } else if (n->left == nullptr) {
                    *ptr = n->right;
                    n->right->parent = parent;
                } else if (n->right == nullptr) {
                    *ptr = n->left;
                    n->left->parent = parent;
                } else {
                    auto l = n->left, r = l->right;
                    if (r == nullptr) {
                        l->right = n->right;
                        l->parent = parent;
                        n->right->parent = l;
                        *ptr = l;
                    } else {
                        while (r->right != nullptr)
                            l = r, r = r->right;
                        l->right = r->left;
                        if (l->right != nullptr)
                            l->right->parent = l;
                        r->left = n->left;
                        r->right = n->right;
                        r->parent = parent;
                        n->left->parent = r;
                        n->right->parent = r;
                        *ptr = r;
                    }
                }
                delete n;
                return;
            }
            parent = n;
            ptr = data < n->data ? &n->left : &n->right;
        }
    }

    iterator begin() const {
        if (!root)
            return end();
        auto n = root;
        while (n->left != nullptr)
            n = n->left;
        return iterator(n);
    }

    iterator end() const {
        return iterator(nullptr);
    }
};

int main() {
    tree<int> ours;
    std::set<int> theirs;
    int op_counts[] = {0, 0, 0, 0};
    for (int i = 0; i < 1000000; ++i) {
        int data = rand() % 100;
        int op = rand() % 4;
        ++op_counts[op];
        if (op == 0) {
            assert(ours.contains(data) == theirs.count(data));
        } else if (op == 1) {
            ours.insert(data);
            theirs.insert(data);
        } else if (op == 2) {
            ours.remove(data);
            theirs.erase(data);
        } else {
            assert(op == 3);
            auto our_it = ours.begin();
            auto their_it = theirs.begin();
            while (our_it != ours.end() && their_it != theirs.end())
                assert(*our_it++ == *their_it++);
            assert(our_it == ours.end() && their_it == theirs.end());
        }
    }

    for (int n : op_counts)
        assert(n > 0);
    std::cout << "All tests passed" << std::endl;
    return 0;
}
