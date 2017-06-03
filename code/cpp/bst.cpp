#include <iostream>
#include <set>
#include <cassert>

template <typename T>
struct node {
    T data;
    node *left = nullptr, *right = nullptr;
    node(T data): data(data) {}
};

template <typename T>
class tree {
    node<T>* root = nullptr;

public:
    bool contains(const T& data) {
        auto curr = root;
        while (curr != nullptr) {
            if (curr->data == data)
                return true;
            curr = data < curr->data ? curr->left : curr->right;
        }
        return false;
    }

    void insert(T data) {
        auto ptr = &root;
        while (auto curr = *ptr) {
            if (curr->data == data)
                return;
            ptr = data < curr->data ? &curr->left : &curr->right;
        }
        *ptr = new node<T>(data);
    }

    void remove(const T& data) {
        auto ptr = &root;
        while (auto curr = *ptr) {
            if (data == curr->data) {
                if (curr->left == nullptr) {
                    *ptr = curr->right;
                } else if (curr->right == nullptr) {
                    *ptr = curr->left;
                } else {
                    auto l = curr->left, r = l->right;
                    if (r == nullptr) {
                        l->right = curr->right;
                        *ptr = l;
                    } else {
                        while (r->right != nullptr)
                            l = r, r = r->right;
                        l->right = r->left;
                        r->left = curr->left;
                        r->right = curr->right;
                        *ptr = r;
                    }
                }
                delete curr;
                return;
            }
            ptr = data < curr->data ? &curr->left : &curr->right;
        }
    }
};

int main() {
    tree<int> ours;
    std::set<int> theirs;
    int op_counts[] = {0, 0, 0};
    for (int i = 0; i < 100000; ++i) {
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
